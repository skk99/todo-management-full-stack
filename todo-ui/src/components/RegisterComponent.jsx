import React, { useState } from 'react'
import { registerAPICall } from '../services/AuthService'
import { useNavigate } from 'react-router-dom'

const RegisterComponent = () => {

    const [name, setName] = useState('')
    const [username, setUsername] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const navigator = useNavigate()

    function handleRegistrationForm(event) {
        event.preventDefault();

        if (name === "" || username === "" || email === "" || password === "") {
            window.alert("name, username, email or password cannot be empty. Please try again!")
        }
        else {
            const register = {name, username, email, password}

            registerAPICall(register).then((response) => {
                console.log(response.data)
                navigator('/login')
            }).catch((err) => {
                console.error(err)
            })
        }
    }

    return (
        <div className='container'>
            <br/> <br/>
            <div className='row'>
                <div className='col-md-6 offset-md-3'>
                    <div className='card'>
                        <div className='card-header'>
                            <h2 className='text-center'>User Registration Form</h2>
                        </div>

                        <div className='card-body'>
                            <form>
                                <div className='row mb-3'>
                                    <label className='col-md-3 control-label'>Name</label>
                                    <div className='col-md-9'>
                                        <input
                                            className='form-control'
                                            type='text'
                                            name='name'
                                            placeholder='Enter name'
                                            value={name}
                                            onChange={(event) => setName(event.target.value)}
                                        >
                                        </input>
                                    </div>
                                </div>

                                <div className='row mb-3'>
                                    <label className='col-md-3 control-label'>Username</label>
                                    <div className='col-md-9'>
                                        <input
                                            className='form-control'
                                            type='text'
                                            name='username'
                                            placeholder='Enter username'
                                            value={username}
                                            onChange={(event) => setUsername(event.target.value)}
                                        >
                                        </input>
                                    </div>
                                </div>

                                <div className='row mb-3'>
                                    <label className='col-md-3 control-label'>Email</label>
                                    <div className='col-md-9'>
                                        <input
                                            className='form-control'
                                            type='text'
                                            name='email'
                                            placeholder='Enter email'
                                            value={email}
                                            onChange={(event) => setEmail(event.target.value)}
                                        >
                                        </input>
                                    </div>
                                </div>

                                <div className='row mb-3'>
                                    <label className='col-md-3 control-label'>Password</label>
                                    <div className='col-md-9'>
                                        <input
                                            className='form-control'
                                            type='password'
                                            name='password'
                                            placeholder='Enter password'
                                            value={password}
                                            onChange={(event) => setPassword(event.target.value)}
                                        >
                                        </input>
                                    </div>
                                </div>

                                <div className='form-group mb-3'>
                                    <button className='btn btn-primary' onClick={(event) => handleRegistrationForm(event)}>Submit</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default RegisterComponent