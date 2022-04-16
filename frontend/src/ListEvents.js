import React, { useEffect, useState } from 'react';
import { useAuthHeader } from 'react-auth-kit';
import axios from 'axios';
import { toast } from 'react-toastify';
import Sales from './pages/Sales'


const ListEvents = ({setId}) => {
    
    const token = localStorage.getItem('atoken');
    const config = { headers: { Authorization : `Bearer ${token}`} };
    const [events, setEvents] = useState([]);
    //const [id, setId] = useState('');

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
            <table>
                <caption>Select event:</caption>
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
                        <td><button onClick = {() => {setId(event.eventId)}}>Select</button></td>
                    </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
    
};

export default ListEvents;