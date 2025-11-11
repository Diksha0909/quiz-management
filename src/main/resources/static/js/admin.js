const api='/api';
document.addEventListener('DOMContentLoaded',()=>{document.getElementById('btn-create-quiz').onclick=createQuiz; loadQuizzes();});
async function loadQuizzes(){const res=await fetch(api+'/admin/quizzes'); const data=await res.json(); const tb=document.getElementById('quiz-table'); tb.innerHTML='';
 data.forEach(q=>{const tr=document.createElement('tr');
  tr.innerHTML=`<td>${q.title}</td><td><a href='/questions.html?quizId=${q.id}'>Open</a> <button onclick='delQuiz(${q.id})'>Delete</button></td>`; tb.appendChild(tr);});}
async function createQuiz(){const title=document.getElementById('quiz-title').value.trim(); if(!title) return; await fetch(api+'/admin/quizzes',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({title})}); document.getElementById('quiz-title').value=''; loadQuizzes();}
async function delQuiz(id){await fetch(api+'/admin/quizzes/'+id,{method:'DELETE'}); loadQuizzes();}
