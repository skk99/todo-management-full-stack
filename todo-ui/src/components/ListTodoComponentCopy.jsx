import React, { useState } from 'react'

// Use Dummy data at the beginning

const ListTodoComponentCopy = () => {

    const dummyData = [
        {
            "id": 1,
            "title": "Learn Core Java",
            "description": "Learn Core Java with Examples",
            "completed": false
        },
        {
            "id": 2,
            "title": "Learn Spring with Java",
            "description": "Learn Spring with Java with Examples",
            "completed": false
        },
        {
            "id": 3,
            "title": "Learn Spring Boot",
            "description": "Learn Spring Boot with Examples",
            "completed": false
        }
    ]

    const [todos, setTodos] = useState(dummyData)

    return (
        <div className='container'>
            <h2 className='text-center'>List of Todos</h2>
            <div>
                <table className='table table-bordered table-striped'>
                    <thead>
                        <tr>
                            <th>Todo Title</th>
                            <th>Todo Description</th>
                            <th>Todo Completed</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            todos.map(todo =>
                                <tr key={todo.id}>
                                    <td>{todo.title}</td>
                                    <td>{todo.description}</td>
                                    <td>{todo.completed ? 'YES': 'NO'}</td>
                                    <td></td>
                                </tr>
                            )
                        }                        
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default ListTodoComponentCopy