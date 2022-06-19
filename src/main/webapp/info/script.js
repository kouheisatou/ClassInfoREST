'use strict'

console.log('佐藤')
document.getElementById('form').onsubmit = function() {
    const overview = document.getElementById('class_overview');
    document.getElementById('class_overview').textContent = '$overview';
};