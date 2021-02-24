# E-Commerce History
A System that can collect (not at the moment) and store price history of products for E-commerce Website, like Tiki/Lazada/Shopee/Sendo (Only Shopee right now).

## Prerequisites
- Docker

## Installing
Run 'docker-compose' for the project, and wait for data to load

## Functionalities
- User can see all products 
- User can see all available categories
- User can see products in categories
- User can Login/Register/Logout
- User can go to the real Shopee product page
- User can perform Full-Text Search / Sort the product based on Price, Relevancy, Rating
- User can see products relevant to the current viewing one

## Limitations
- System can not **automatically** perform data crawling from Shopee in Real Time (due to Shopee API limitation)
- User can not see price history of a product from time to time (due to lack of time)
- System is running locally (not successfully to implement Google Cloud)
- etc.


## Architecture
![Something](https://github.com/hoangesoftblog/SADI_GroupAssignment/blob/master/Overall%20Architecture.png?raw=true)
