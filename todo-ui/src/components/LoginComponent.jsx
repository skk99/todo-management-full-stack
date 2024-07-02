import React, { useState } from 'react'
import { loginAPICall, saveLoggedInUser, storeToken } from '../services/AuthService';
import { useNavigate } from 'react-router-dom';

const LoginComponent = () => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const navigator = useNavigate()

    async function handleLoginForm(event) {
        event.preventDefault();

        await loginAPICall(username, password).then((response) => {
            console.log(response.data)
            // store basic auth token in browser local storage
            // const token = "Basic " + window.btoa(username + ":" + password)

            // store jwt token
            const token = "Bearer " + response.data.accessToken
            storeToken(token)

            // get role
            const role = response.data.role

            saveLoggedInUser(username, role)
            navigator('/todos')
            window.location.reload(false)
        }).catch((err) => {
            console.error(err)
            window.alert("Invalid Username or Password. Please try again !")
        })
    }

    return (
        <div className='container'>
            <br/> <br/>
            <div className='row'>
                <div className='col-md-6 offset-md-3'>
                    <div className='card'>
                        <div className='card-header'>
                            <h2 className='text-center'>User Login Form</h2>
                        </div>

                        <div className='card-body'>
                            <form>
                                <div className='row mb-3'>
                                    <label className='col-md-3 control-label'>Username</label>
                                    <div className='col-md-9'>
                                        <input
                                            className='form-control'
                                            type='text'
                                            name='username'
                                            placeholder='Enter username or email'
                                            value={username}
                                            onChange={(event) => setUsername(event.target.value)}
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
                                    <button className='btn btn-primary' onClick={(event) => handleLoginForm(event)}>Submit</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LoginComponent