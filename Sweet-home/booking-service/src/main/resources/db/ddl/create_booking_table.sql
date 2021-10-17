use sweethome;
CREATE TABLE booking (
    bookingId int NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    fromDate Date, 
    toDate Date, 
    aadharNumber VARCHAR(50),
    numOfRooms int,
    roomNumbers VARCHAR(255),
    roomPrice int NOT NULL,
    transactionId int DEFAULT 0,
    bookedOn Date
);