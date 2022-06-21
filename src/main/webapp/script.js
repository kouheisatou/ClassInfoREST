'use strict'
/*
document.getElementById('form').onsubmit = function() {
    //検索フォームに入力した内容とプルダウンの情報を含んだURLをhtmlのhrefに代入する
    location.href = document.getElementById('form').select.value;
    console.log('クリックされました')
};*/
/*
let classname
let classCode
*/
let className //nameだとエラー
let classCode
let department
let grade
let unitDivision
let semester
let campas
let urlDict={}
let urlList=[]


document.getElementById('name').onchange = function(){
    className=document.getElementById('name').value;
    console.log(className);
    urlDict['name']=className;
    console.log(urlDict);
}


document.getElementById('classCode').onchange = function(){
    classCode=document.getElementById('classCode').value;
    console.log(classCode);
    urlDict['classCode']=classCode;
    console.log(urlDict);
}

document.getElementById('grade').onchange = function(){
    grade=document.getElementById('grade').value;
    console.log(grade);
    urlDict['grade']=grade;
    console.log(urlDict);
}

document.getElementById('unitDivision').onchange = function(){
    unitDivision=document.getElementById('unitDivision').value;
    console.log(unitDivision);
    urlDict['unitDivision']=unitDivision;
    console.log(urlDict);
}

document.getElementById('semester').onchange = function(){
    semester=document.getElementById('semester').value;
    console.log(semester);
    urlDict['semester']=semester;
    console.log(urlDict);
}

document.getElementById('campas').onchange = function(){
    campas=document.getElementById('campas').value;
    console.log(campas);
    urlDict['campas']=campas;
    console.log(urlDict);
}

function search(){
    //検索フォームに入力した内容とプルダウンの情報を含んだurlListをhtmlのhrefに代入する
    for(let key in urlDict){
        if(urlDict[key]==""){
            delete urlDict[key];
        }
    }

    let count=0;
    let URL='';
    let link="https://tonarinosibahaao.iobb.net:8181/ClassInfo/rest/class/get/?";
    for(let key in urlDict){
        URL+=(key+"="+urlDict[key]);
        if(count==Object.keys(urlDict).length-1)break;
        URL+='&';
        count++;
    }
    link+=URL;
    console.log(URL);
    location.href=link;
    console.log('検索がクリックされました')
}

function deleteText(id){
    document.getElementById(id).value='';
}


