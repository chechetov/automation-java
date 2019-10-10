#! /usr/bin/python

import requests
import json

def createBooking():

	""" Creates booking from 2019-04-27 to 2019-04-30 without deposit with price of 2389 """
	""" Returns status code and json if status code is 200 """

	url = "https://restful-booker.herokuapp.com/booking"
	data = {
    'firstname' : 'Denys',
    'lastname' : 'Smith',
    'totalprice' : '2389',
    'depositpaid' : 'false',
    'bookingdates' :{
        'checkin' : '2019-04-27',
        'checkout' : '2019-04-30'
    },
    'additionalneeds' : 'Breakfast'
	}

	headers = {'Content-type': 'application/json'}
	res = requests.post(url, data=json.dumps(data), headers=headers)
	res_json    = res.json()

	assert ( res.status_code == 200, "Code is not 200 for getBooking" )
	return ( res.status_code, res_json ) 

def getBooking(bookingId):

	""" Gets the booking by ID """

	res = requests.get('https://restful-booker.herokuapp.com/booking/{:d}'.format(bookingId))
	res_json    = res.json()
	
	assert res.status_code == 200, "Code is not 200 for getBooking"
	return ( res.status_code, res_json) 


def checkEqualityofBookings(jsonData1, jsonData2):
	assert cmp(jsonData2, jsonData2 == 0), "Data for createdBooking does NOT MATCH data for getBooking"

# Creating booking
createdBooking = createBooking();
print("\n createdBooking: type {}, value {}").format(type(createdBooking), createdBooking)

# Getting booking
getBooking = getBooking(createdBooking[1]["bookingid"])
print("\n getCreatedBooking: type {}, value {}").format(type(getBooking), getBooking)

# Checking for equality
checkEqualityofBookings(createdBooking[1]["booking"], getBooking[1])



