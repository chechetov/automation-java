from pyunitreport import HTMLTestRunner
import unittest
import unittest.mock
import requests
import json
import logging
import sys


class Monolithic(unittest.TestCase):

    log = logging.getLogger("TestLog")

    def make_orderer():
        order = {}

        def ordered(f):
            order[f.__name__] = len(order)
            return f

        def compare(a, b):
            return [1, -1][order[a] < order[b]]

        return ordered, compare

    ordered, compare = make_orderer()
    unittest.defaultTestLoader.sortTestMethodsUsing = compare

    bookingId  = 0

    bookingData_createBooking = json.loads('{"test":"test"}')
    bookingData_getBooking = json.loads('{"test":"test"}')

    """ Example test for HtmlRunner. """
    @ordered
    def test_createBooking(self):
        
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

        type(self).bookingId = res_json["bookingid"] 
        type(self).bookingData_createBooking = res_json["booking"]

        assert  res.status_code == 200, "Code is not 200 for getBooking"
        
        #return ( res.status_code, res_json ) 

    @ordered
    def test_getBooking(self):

        """ Gets the booking by ID """
        self.log.info("booking ID in get: {}".format(self.bookingId))
        
        res = requests.get('https://restful-booker.herokuapp.com/booking/{:d}'.format(self.bookingId))
        res_json    = res.json()

        type(self).bookingData_getBooking = res_json
        

        assert res.status_code == 200, "Code is not 200 for getBooking"
        # return ( res.status_code, res_json) 

    @ordered
    def test_checkEqualityofBookings(self):
        #
        self.log.info(self.bookingData_createBooking)

        self.log.info(self.bookingData_getBooking)

        assert self.bookingData_createBooking == self.bookingData_getBooking, "Data for createdBooking does NOT MATCH data for getBooking"
   
if __name__ == '__main__':
    logging.basicConfig(stream=sys.stderr, level=logging.INFO)
    unittest.main(testRunner=HTMLTestRunner(output='report'))
