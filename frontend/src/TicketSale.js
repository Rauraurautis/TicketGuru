import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Sales from './pages/Sales'
import { useNavigate } from 'react-router-dom';

const TicketSale = (props) => {
    //Access-token
    //const token = localStorage.getItem('atoken');
    //const config = { headers: { Authorization : `Bearer ${token}`} };

    const config = {
        withCredentials: true
    }

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
        })
        .catch(console.log)
        alert(`Sold ${salesObject.nrOfTickets} tickets for ${props.event.eventTitle}, total sum: ${totalPrice}€`)
        goBack('../secure')
    }


    return (
        <div>
            <h2>{props.event.eventTitle}, {props.event.dateOfEvent}</h2>
            <form onSubmit={handleSubmit}>
                <label>
                Select a tickettype:
                <select value={salesObject.ticketTypeId} name="ticketTypeId" onChange={handleChange}>
                    <option>Empty</option>
                    {listOfTT.map(tt =>(
                        <option value={tt.ticketTypeId}>{tt.ticketTypeDescription}, {tt.price}€</option>
                    ))}
                </select>
                </label>
                <p></p>
                <label>
                Number of tickets:
                <input type="text" value={salesObject.nrOfTickets} name="nrOfTickets" onChange={handleChange}></input>
                </label>
                <p></p>
                <label>
                Number of discounted tickets:
                <input type="text" value={salesObject.nrOfDiscounted} name="nrOfDiscounted" onChange={handleChange}></input>
                </label>
                <p></p>
                <label>
                Discount percentage as decimal(i.e. 0.5 for 50%):
                <input type="text" value={salesObject.discountPercentage} name="discountPercentage" onChange={handleChange}></input>
                </label>
                {totalPrice !== '' &&
                    <div>Total price:{totalPrice}€</div>
                }
                <p></p>
                <button type="button" onClick={() => countTotalPrice()}>Check sum total</button>
                <p></p>
                {totalPrice !== '' &&
                    <input type="submit" value="Confirm purchase"/>
                }
                
                
                
            </form>
            
            <button onClick = { () => props.setEvent('')}>Cancel</button>
        </div>
    )

};

export default TicketSale;