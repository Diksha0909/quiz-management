const api = '/api';

document.addEventListener('DOMContentLoaded', () => loadList());

async function loadList() {
    const res = await fetch(api + '/public/quizzes');
    const list = await res.json();

    const tb = document.getElementById('quiz-table');
    tb.innerHTML = '';

    list.forEach(q => {
        const tr = document.createElement('tr');

        tr.innerHTML = `
            <td>${q.title}</td>
            <td><a href='/quiz-take.html?quizId=${q.id}'>Start Quiz</a></td>
        `;

        tb.appendChild(tr);
    });
}
