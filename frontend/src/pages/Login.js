import axios from 'axios';
import React, { useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { useAuthUser, useIsAuthenticated, useSignIn } from 'react-auth-kit';
import { toast } from 'react-toastify';
import { Form, Button } from 'react-bootstrap'


const Login = () => {

    const [userDetails, setUserDetails] = useState({ username: "", password: "" });

    const isAuthenticated = useIsAuthenticated();
    const signIn = useSignIn();
    const navigate = useNavigate();

    const handleChange = (e) => {
        setUserDetails({ ...userDetails, [e.target.name]: e.target.value });
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/login', userDetails)
            .then(res => {
                if (res.status === 200) {
                    if (signIn({
                        token: res.data.access_token,
                        //tokenType: 'Bearer',
                        expiresIn: 20, //raukeamisaika vedetty hatusta, refreshtoken pitää implementoida kunnolla myöhemmin
                        authState: {name: userDetails.username}
                    })) {
                        localStorage.setItem('atoken', res.data.access_token);//purkkaratkaisu kun en muuta keksinyt
                        navigate('/secure');
                    } else {
                        toast.warn("There was some problem with logging in");
                    }
                } else {
                    toast.warn("There was some problem with logging in");
                }
            })
            .catch(e => {
                toast.warn("Wrong username or password!");
            });
    };

    if (isAuthenticated()) {
        return (<Navigate to={'/secure'} replace/>);
    } else {
        return (
            <div>
                <h2></h2>
                <Form onSubmit={handleSubmit}>
                    <Form.Group>
                        <Form.Label>Username:</Form.Label>
                        <Form.Control type="text" placeholder="Type your username" value={userDetails.username} name="username" onChange={handleChange}/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Password:</Form.Label>
                        <Form.Control type="text" placeholder="Type your password" value={userDetails.password} name="password" onChange={handleChange}/>
                    </Form.Group>
                    <p></p>
                    <Button variant="primary" type="submit">
                        Submit
                    </Button>
                </Form>
            </div>
        );
    }

};

export default Login;