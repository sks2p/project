const toggleBtn = document.querySelector('.toogleBtn');
const menu = document.querySelector('.menu');
const icons = document.querySelector('.icons');

toggleBtn.addEventListener('click', () => {
    menu.classList.toggle('active');
    icons.classList.toggle('active');
});

