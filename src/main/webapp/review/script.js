'use strict'

console.log('佐藤')/*
document.getElementById('form').onsubmit = function() {
    //検索フォームに入力した内容とプルダウンの情報を含んだURLをhtmlのhrefに代入する
    location.href = document.getElementById('form').select.value;
    console.log('クリックされました')
};*/

/*
document.getElementById('form').onchange = function() {
    //検索フォームに入力した内容とプルダウンの情報を含んだURLをhtmlのhrefに代入する
    location.href = document.getElementById('form').select.value;
    console.log('クリックされました')
};*/


let department_field
let grade
let Unit_division
let Campas

document.getElementById('form').onchange = function() {
    department_field=document.getElementById('department_field');
    grade=document.getElementById('grade');
    Unit_division=document.getElementById('Unit_division');
    Campas=document.getElementById('Campas');
}
console.log(department_field);