import React, { useEffect, useState } from 'react';
import { useAuthHeader } from 'react-auth-kit';
import axios from 'axios';
import { toast } from 'react-toastify';
import Sales from './pages/Sales'


const ListEvents = ({setEvent}) => {
    //Access-token
    const token = localStorage.getItem('atoken');
    const config = { headers: { Authorization : `Bearer ${token}`} };
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
                        <td><button onClick = {() => {setEvent(event)}}>Select</button></td>
                    </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
    
};

export default ListEvents;