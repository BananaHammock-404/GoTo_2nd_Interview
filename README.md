# GoTo_2nd_Interview

Please create web automation (Mandatory to use Java, any framework*) including both positive and negative cases for the checkout process on Demo store using Credit Card as payment method.

Testing card:
- Number: 4811111111111114
- Expiry month-year: {any}
- CVV: 123

Rules Found:
- There is only email format verification for checkout. Any other field is ignored
- You can input a price lower than the discount, and it would result in a data error when paying
- The price more than 100 Billion will result in checkout error
- bigger than year 2035 exp date not valid
- Less than today month/year exp date not valid
- 4th Try when declined by Bank Authentication, you are directed to HomePage (Something like your account is blocked probably)