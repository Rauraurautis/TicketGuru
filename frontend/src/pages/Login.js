import axios from 'axios';
import React, { useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { useAuthUser, useIsAuthenticated, useSignIn } from 'react-auth-kit';
import { toast } from 'react-toastify';
import { Form, Button, Container } from 'react-bootstrap'
import Spinner from 'react-bootstrap/Spinner'


const Login = () => {

    const [userDetails, setUserDetails] = useState({ username: "", password: "" });
    const [loading, setLoading] = useState(false)

    const isAuthenticated = useIsAuthenticated();
    const signIn = useSignIn();
    const navigate = useNavigate();

    const handleChange = (e) => {
        setUserDetails({ ...userDetails, [e.target.name]: e.target.value });
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true)
        axios.post('http://localhost:8080/login', userDetails)
            .then(res => {
                if (res.status === 200) {
                    setLoading(false)
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
                setLoading(false)
                toast.warn("Wrong username or password!");
            });
    };

    if (isAuthenticated()) {
        return (<Navigate to={'/secure'} replace/>);
    } else {
        return (
            <Container className="d-flex justify-content-center mt-5">
                {loading ?  <div style={{width: "100%", display: "flex", justifyContent: "center"}}><Spinner animation="border"  /></div> : 
                    <Form onSubmit={handleSubmit} className="d-flex flex-column">
                    <Form.Group>
                        <Form.Label>Username:</Form.Label>
                        <Form.Control type="text" placeholder="Type your username" value={userDetails.username} name="username" onChange={handleChange}/>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Password:</Form.Label>
                        <Form.Control type="password" placeholder="Type your password" value={userDetails.password} name="password" onChange={handleChange}/>
                    </Form.Group>
                    <p></p>
                    <Button variant="primary" type="submit" className="w-50 mt-2 align-self-center">
                        Submit
                    </Button>
                </Form>}
            </Container>
        );
    }

};

export default Login;