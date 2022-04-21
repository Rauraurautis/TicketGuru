import React, { useEffect, useState } from 'react';
import { useAuthHeader } from 'react-auth-kit';
import axios from 'axios';
import { toast } from 'react-toastify';
import Sales from './pages/Sales'
import { Table, Button } from 'react-bootstrap'


const ListEvents = ({setEvent}) => {
    //Access-token
    //const token = localStorage.getItem('atoken');
    //const config = { headers: { Authorization : `Bearer ${token}`} };

    const config = {
        withCredentials: true
    }

    //Event-lista
    const [events, setEvents] = useState([]);

    //Eventtien haku bäkkäriltä
    const hook = () => {
        axios.get('http://localhost:8080/api/events', config)
        .then(res => {
            setEvents(res.data);
        })
        .catch(console.log);
    };

    useEffect(hook, [])
    

    return (
        <div>
            <h3>Select event:</h3>
            <Table striped bordered hover>
                <thead>
                    <tr>
                    <th>Name</th>
                    <th>Date</th>
                    <th>Venue</th>
                    </tr>
                </thead>
                <tbody>
                    {events.map(event => (
                    <tr key={event.eventId}>
                        <td>{event.eventTitle}</td>
                        <td>{event.dateOfEvent}</td>
                        <td>{event.eventVenue.venueName}</td>
                        <td><Button onClick = {() => {setEvent(event)}}>Select</Button></td>
                    </tr>
                    ))}
                </tbody>
            </Table>
        </div>
    );
    
};

export default ListEvents;