function openPopupCenter(url, title, w, h) {
  // 브라우저 창의 크기 가져오기
  const screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
  const screenHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;

  // 팝업 창의 위치 계산
  const left = (screenWidth - w) / 2;
  const top = (screenHeight - h) / 2;

  // 팝업 창 열기
  window.open(url, title, `width=${w}, height=${h}, left=${left}, top=${top}`);
}
