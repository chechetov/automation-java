#! /usr/bin/python3

import requests
import json

def createBooking():
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
	return ( res.status_code, res_json) 

def getBooking(bookingId):
	res = requests.get('https://restful-booker.herokuapp.com/booking/{:d}'.format(bookingId))
	res_json    = res.json()
	return ( res.status_code, res_json) 

# Making createdBooking request
createdBooking = createBooking();
print("\n createdBooking: type {}, value {}").format(type(createdBooking), createdBooking)

createdBookingjsonData = createdBooking[1]
bookingId = createdBookingjsonData["bookingid"]
bookingInfo = createdBookingjsonData["booking"]

getCreatedBooking = getBooking(bookingId)
print("\n getCreatedBooking: type {}, value {}").format(type(getCreatedBooking), getCreatedBooking)
getCreatedBookingjsonData = getCreatedBooking[1]

try:
	assert (cmp(bookingInfo, getCreatedBookingjsonData) == 0)
	print("\n Data obtained from response of createBooking did match data obtained from getCreatedBooking")
	exit(0)
except AssertionError as error:
	print("\n Data obtained from response of createBooking did NOT match data obtained from getCreatedBooking")
	# No need for this actually
	exit(1)

