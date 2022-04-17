import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Sales from './pages/Sales'

const TicketSale = (props) => {
    const token = localStorage.getItem('atoken');
    const config = { headers: { Authorization : `Bearer ${token}`} };
    
    //Tickettype-lista
    const [listOfTT, setListOfTT] = useState([]);

    //Statet salesobjectille
    const [ticketType, setTicketType] = useState('');
    const [nrOfTickets, setNrOfTickets] = useState('');
    const [nrOfDiscounted, setNrOfDiscounted] = useState('');
    const [discountP, setDiscountP] = useState('');

    const hook = () => {
        axios.get(`http://localhost:8080/api/events/${props.event.eventId}/tickettypes`, config)
        .then(res => {
            setListOfTT(res.data);
            console.log(res.data);
        })
        .catch(console.log);
    };

    useEffect(hook, [])

    return (
        <div>
            {props.event.eventId}
        </div>
    )

};

export default TicketSale;