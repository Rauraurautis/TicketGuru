import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Sales from './pages/Sales'
import { Navigate, useNavigate } from 'react-router-dom';
import { Form, Button } from 'react-bootstrap'
import { toast } from 'react-toastify';

const TicketSale = (props) => {
    //Access-token
    //const token = localStorage.getItem('atoken');
    //const config = { headers: { Authorization : `Bearer ${token}`} };

    const config = {
        withCredentials: true
    }
    const navigate = useNavigate();
    const goBack = useNavigate('./secure');
    
    //Tickettype-lista
    const [listOfTT, setListOfTT] = useState([]);

    //TicketType-listan haku bäkkäriltä
    const hook = () => {
        axios.get(`http://localhost:8080/api/events/${props.event.eventId}/tickettypes`, config)
        .then(res => {
            setListOfTT(res.data);
            console.log(res.data);
        })
        .catch(console.log);
    };

    useEffect(hook, [])

    //State salesobjectille
    const [salesObject, setSalesObject] = useState({ ticketTypeId: '', nrOfTickets: 0, nrOfDiscounted: 0, discountPercentage: 0});
    const [totalPrice, setTotalPrice] = useState("");

    
    
    const handleChange = (e) => {
        setSalesObject({...salesObject, [e.target.name]: e.target.value});
    }

    //Laskee kokonaishinnan ruudulle
    const countTotalPrice = () => {
        if(salesObject.ticketTypeId == 'Open to select' || salesObject.ticketTypeId == '') {
            toast.warn('You must choose a tickettype first')
        }
        const index = listOfTT.findIndex(tt => {
            return tt.ticketTypeId == salesObject.ticketTypeId;
        });
        
        const countPrice = () => {
            if(salesObject.nrOfDiscounted == 0 || salesObject.discountPercentage == 0) {
                return listOfTT[index].price * salesObject.nrOfTickets;
            } else {
                return (listOfTT[index].price * (salesObject.nrOfTickets - salesObject.nrOfDiscounted)) + ((1 - salesObject.discountPercentage) * listOfTT[index].price * salesObject.nrOfDiscounted)
            }
        }
        const price = countPrice();
        console.log(price)
        setTotalPrice(price);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/api/sales', [salesObject], config)
        .then(res => {
            console.log(res.status);
            navigate('/receipts', { state: res.data })  //siirtyy pagelle Receipts.js ja vie sinne, hölmösti, stateen salespostista saadun isokokoisen jsonin
        })
        .catch(console.log)
        //alert(`Sold ${salesObject.nrOfTickets} tickets for ${props.event.eventTitle}, total sum: ${totalPrice}€`)
        //goBack('../secure')
    }


    return (
        <div>
            <h2>{props.event.eventTitle}, {props.event.dateOfEvent}</h2>
            <Form onSubmit={handleSubmit}>
                <Form.Group>
                <Form.Label>Select a tickettype:</Form.Label>
                <Form.Select value={salesObject.ticketTypeId} name="ticketTypeId" onChange={handleChange}>
                    <option>Open to select</option>
                    {listOfTT.map(tt =>(
                        <option value={tt.ticketTypeId}>{tt.ticketTypeDescription}, {tt.price}€</option>
                    ))}
                </Form.Select>
                </Form.Group>
                <p></p>
                <Form.Group>
                    <Form.Label>Number of tickets:</Form.Label>
                    <Form.Control type="number" value={salesObject.nrOfTickets} name="nrOfTickets" onChange={handleChange}/>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Number of discounted tickets:</Form.Label>
                    <Form.Control type="text" value={salesObject.nrOfDiscounted} name="nrOfDiscounted" onChange={handleChange}/>
                </Form.Group>
                <p></p>
                <Form.Group>
                    <Form.Label>Discount percentage as decimal(i.e. 0.5 for 50%):</Form.Label>
                    <Form.Control type="text" value={salesObject.discountPercentage} name="discountPercentage" onChange={handleChange}/>
                </Form.Group>
                <p></p>
                {totalPrice !== '' &&
                    <div>Total price:{totalPrice}€</div>
                }
                <p></p>
                <Button type="button" onClick={() => countTotalPrice()}>Check sum total</Button>
                <p></p>
                {totalPrice !== '' &&
                    <Button type="submit">Confirm purchase</Button>
                }
                
                
                
            </Form>
            <p></p>
            <Button onClick={() => props.setEvent('')}>Cancel</Button>
        </div>
    )

};

export default TicketSale;