import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from 'react-bootstrap'

const Home = () => {
    const navigate = useNavigate();

    return (
        <div>
            <h2>Triforce TicketGuru</h2>
            <Button onClick={()=> navigate('/login')}>Go to Login</Button>
        </div>
    )
};

export default Home;