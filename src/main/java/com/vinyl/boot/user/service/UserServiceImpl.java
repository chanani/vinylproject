package com.vinyl.boot.user.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vinyl.boot.command.naverLogin.NaverLoginProfile;
import com.vinyl.boot.command.naverLogin.NaverLoginProfileResponse;
import com.vinyl.boot.command.naverLogin.NaverLoginVo;
import com.vinyl.boot.command.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;




import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WebClient webClient;

    @Value("${api.naver.client_id}")
    private String n_client_id;

    @Value("${api.naver.client_secret}")
    private String n_client_secret;

    @Value("${api.kakao.client_id}")
    private String k_client_id;

    @Override
    public int addJoin(UserVO vo) {
        return userMapper.addJoin(vo);
    }

    @Override
    public String checkId(String username) {
        return userMapper.checkId(username);
    }

    @Override
    public UserVO login(String username) {
        return userMapper.login(username);
    }

    @Override
    public int checkEmail(String username, String email) {
        return userMapper.checkEmail(username, email);
    }

    @Override
    public int modifyPassword(String username, String password) {
        return userMapper.modifyPassword(username, password);
    }

    @Override
    public String getAccessToken(String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");

            sb.append("&client_id=" + this.k_client_id); //본인이 발급받은 key
            sb.append("&redirect_uri=http://localhost:8888/kakaoLogin"); // 본인이 설정한 주소

            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_Token;
    }

    @Override
    public HashMap<String, Object> getUserInfo(String access_Token) {
        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    @Override
    public String socialCheckEmail(String email) {
        return userMapper.socialCheckEmail(email);
    }

    @Override
    public String getUsername(String email) {
        return userMapper.getUsername(email);
    }

    @Override
    public NaverLoginVo requestNaverLoginAcceccToken(Map<String, String> resValue, String grant_type){

        final String uri = UriComponentsBuilder
                .fromUriString("https://nid.naver.com")
                .path("/oauth2.0/token")
                .queryParam("grant_type", grant_type)
                .queryParam("client_id", this.n_client_id)
                .queryParam("client_secret", this.n_client_secret)
                .queryParam("code", resValue.get("code"))
                .queryParam("state", resValue.get("state"))
                .queryParam("refresh_token", resValue.get("refresh_token")) // Access_token 갱신시 사용
                .build()
                .encode()
                .toUriString();

        System.out.println(resValue.get("access_token"));
        return webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(NaverLoginVo.class)
                .block();
    }

    // ----- 프로필 API 호출 (Unique한 id 값을 가져오기 위함) -----
    public NaverLoginProfile requestNaverLoginProfile(NaverLoginVo naverLoginVo){
        final String profileUri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/nid/me")
                .build()
                .encode()
                .toUriString();

        return webClient
                .get()
                .uri(profileUri)
                .header("Authorization", "Bearer " + naverLoginVo.getAccess_token())
                .retrieve()
                .bodyToMono(NaverLoginProfileResponse.class)
                .block()
                .getResponse(); // NaverLoginProfile 은 건네준다.
    }

}
