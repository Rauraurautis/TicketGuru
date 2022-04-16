import React, { useEffect, useState } from 'react';
import { useAuthHeader } from 'react-auth-kit';
import axios from 'axios';
import { toast } from 'react-toastify';


const ListEvents = () => {
    
    const token = localStorage.getItem('atoken');
    const config = { headers: { Authorization : `Bearer ${token}`} };
    const [events, setEvents] = useState([]);

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
                <ul>
                    {events.map(event => <li>{event.eventTitle}</li>)}
                </ul>
          </div>
    );
    
};

export default ListEvents;