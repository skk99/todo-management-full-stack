import React, { useEffect, useState } from 'react'
import { getTodo, saveTodo, updateTodo } from '../services/TodoService'
import { useNavigate, useParams } from 'react-router-dom'

const TodoComponent = () => {

    const [title, setTitle] = useState('')
    const [description, setDescription] = useState('')
    const [completed, setCompleted] = useState(false)

    const navigate = useNavigate()

    // Get the id from url for update-todo
    const {id} = useParams()

    function saveOrUpdateTodo(event) {
        event.preventDefault()

        const todo = {title, description, completed}
        console.log(todo)

        if (id) {
            updateTodo(id, todo).then((response) => {
                navigate('/todos')
            }).catch((err) => {
                console.error(err)
            })
        }
        else {
            saveTodo(todo).then((response) => {
                console.log(response.data)
                navigate('/todos')
            }).catch((error) => {
                console.error(error)
            })
        }
    }

    function pageTitle() {
        if (id) {
            return <h2 className='text-center'>Update Todo</h2>
        }
        return <h2 className='text-center'>Add Todo</h2>
    }

    useEffect(() => {
        if (id) {
            getTodo(id).then((response) => {
                console.log(response.data)
                setTitle(response.data.title)
                setDescription(response.data.description)
                setCompleted(response.data.completed)
            }).catch((err) => {
                console.error(err)
            })
        }
    }, [id])

    return (
        <div className='container'>
            <br/><br/>
            <div className='row'> 
                <div className='card col-md-6 offset-md-3 offset-md-3'>
                    { pageTitle() }
                    <div className='card-body'>
                        <form>
                            <div className='form-group mb-2'>
                                <label className='form-label'>Todo Title</label>
                                <input
                                    className='form-control'
                                    type='text'
                                    placeholder='Enter Todo Title'
                                    name='title'
                                    value={title}
                                    onChange={(event) => setTitle(event.target.value)}
                                ></input>
                            </div>

                            <div className='form-group mb-2'>
                                <label className='form-label'>Todo Description</label>
                                <input
                                    className='form-control'
                                    type='text'
                                    placeholder='Enter Todo Description'
                                    name='description'
                                    value={description}
                                    onChange={(event) => setDescription(event.target.value)}
                                ></input>
                            </div>

                            <div className='form-group mb-2'>
                                <label className='form-label'>Todo Completed</label>
                                <select
                                    className='form-control'
                                    value={completed}
                                    onChange={(event) => setCompleted(event.target.value)}
                                >
                                    <option value='false'>No</option>
                                    <option value='true'>Yes</option>
                                </select>
                            </div>

                            <button className='btn btn-success' onClick={(event) => saveOrUpdateTodo(event)}>Submit</button>

                        </form>
                    </div>
                </div>
            
            </div>
        </div>
    )
}

export default TodoComponent