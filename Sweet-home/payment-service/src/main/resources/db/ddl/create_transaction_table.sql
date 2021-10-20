use sweethome;
CREATE TABLE transaction (
    transactionId int NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    paymentMode VARCHAR(50),
    bookingId int NOT NULL,
    upiId VARCHAR(50),
    cardNumber VARCHAR(50)
    -- FOREIGN KEY (bookingId) REFERENCES booking(bookingId)
);