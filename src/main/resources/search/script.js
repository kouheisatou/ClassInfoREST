'use strict'

console.log('佐藤')
document.getElementById('form').onsubmit = function() {
    //検索フォームに入力した内容とプルダウンの情報を含んだURLをhtmlのhrefに代入する
    location.href = document.getElementById('form').select.value;
};