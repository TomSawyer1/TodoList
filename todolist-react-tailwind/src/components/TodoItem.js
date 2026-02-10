import React from 'react';

const TodoItem = ({ todo, onToggle, onDelete }) => {
  return (
    <li className="p-4 bg-white rounded-lg shadow-sm border border-gray-100 hover:shadow-md transition-shadow">
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-3 flex-1">
          <input
            type="checkbox"
            checked={todo.completed}
            onChange={() => onToggle(todo.id, !todo.completed)}
            className="w-5 h-5 text-blue-600 rounded focus:ring-blue-500 cursor-pointer"
          />
          <span
            className={`flex-1 font-medium text-gray-800 ${
              todo.completed ? 'line-through text-gray-400' : ''
            }`}
          >
            {todo.title}
          </span>
        </div>
        <button
          onClick={() => onDelete(todo.id)}
          className="ml-4 px-3 py-1.5 text-sm text-red-600 hover:text-white hover:bg-red-500 rounded-md transition-colors border border-red-200 hover:border-red-500"
        >
          Supprimer
        </button>
      </div>
      
      {/* Description de la tâche */}
      {todo.description && (
        <p
          className={`mt-2 ml-8 text-sm ${
            todo.completed ? 'text-gray-400 line-through' : 'text-gray-600'
          }`}
        >
          {todo.description}
        </p>
      )}
    </li>
  );
};

export default TodoItem;
