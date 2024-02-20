// JavaScript 코드
document.addEventListener('DOMContentLoaded', () => {
    const addButton = document.getElementById('add-btn');
    const inputField = document.getElementById('todo-input');
    const todoList = document.getElementById('todo-list');
  
    // 로컬 스토리지에서 투두 리스트를 불러오기
    const todos = JSON.parse(localStorage.getItem('todos')) || [];
    todos.forEach(todo => {
      addTodo(todo);
    });
  
    // 투두 추가 기능
    addButton.addEventListener('click', () => {
      const todoText = inputField.value.trim();
      if (todoText) {
        addTodo(todoText);
        todos.push(todoText);
        localStorage.setItem('todos', JSON.stringify(todos));
        inputField.value = '';
      }
    });
  
    function addTodo(text) {
      const li = document.createElement('li');
      const checkBox = document.createElement('input');
      checkBox.type = 'checkbox';
      checkBox.addEventListener('change', function() {
        if (this.checked) {
          li.classList.add('checked');
        } else {
          li.classList.remove('checked');
        }
      });
      
      const span = document.createElement('span');
      span.textContent = text;
  
      const editButton = document.createElement('button');
      editButton.textContent = '수정';
      // 수정 기능을 여기에 구현할 수 있습니다.
  
      const deleteButton = document.createElement('button');
      deleteButton.textContent = '삭제';
      deleteButton.addEventListener('click', function() {
        li.remove();
        const index = todos.indexOf(text);
        if (index > -1) {
          todos.splice(index, 1);
          localStorage.setItem('todos', JSON.stringify(todos));
        }
      });
  
      li.appendChild(checkBox);
      li.appendChild(span);
      li.appendChild(editButton);
      li.appendChild(deleteButton);
      todoList.appendChild(li);
    }
  });
  