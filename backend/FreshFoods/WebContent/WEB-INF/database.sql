SET @STATIC_FILES_URL = "http://localhost:4200";
CREATE TABLE User(
    id VARCHAR(36),
    name VARCHAR(20),
    email VARCHAR(20),
    password VARCHAR(72),
    phone VARCHAR(10),
    addressLine1 VARCHAR(50),
    addressLine2 VARCHAR(50),
    city VARCHAR(20),
    state VARCHAR(20),
    zipcode VARCHAR(10),
    PRIMARY KEY (id),
    UNIQUE(email)
);
CREATE TABLE Item(
    id VARCHAR(10) NOT NULL,
    name VARCHAR(36),
    imageUrl VARCHAR(50),
    price DOUBLE,
    unit VARCHAR(20),
    description TEXT,
    storage TEXT,
    origin TEXT,
    preparation TEXT,
    PRIMARY KEY(id)
);
CREATE TABLE Recipe(
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(50),
    imageUrl VARCHAR(50),
    difficulty VARCHAR(10),
    prepTime INTEGER,
    cookTime INTEGER,
    servingSize INTEGER,
    calories INTEGER,
    protein INTEGER,
    carbohydrates INTEGER,
    sugar INTEGER,
    fibre INTEGER,
    cholestrol INTEGER,
    sodium INTEGER,
    instructions TEXT
);
CREATE TABLE RecipeIngredient(
    recipeId VARCHAR(10),
    ingredientId VARCHAR(10),
    ingredientItemCount INTEGER,
    FOREIGN KEY (recipeId) REFERENCES Recipe(id),
    FOREIGN KEY (ingredientId) REFERENCES Item(id),
    UNIQUE (recipeId, ingredientId)
);
CREATE TABLE Address(
    id VARCHAR(36) PRIMARY KEY,
    addressLine1 VARCHAR(50),
    addressLine2 VARCHAR(50),
    city VARCHAR(20),
    state VARCHAR(20),
    zipcode VARCHAR(10)
);
CREATE TABLE Orders(
    id VARCHAR(36) PRIMARY KEY,
    orderDate VARCHAR(50),
    transactionId VARCHAR(50),
    deliveryDate VARCHAR(50),
    deliveryCost DECIMAL,
    deliveryAddressId VARCHAR(36),
    subTotal DECIMAL,
    totalPrice DECIMAL,
    totalItemCount INTEGER,
    FOREIGN KEY (deliveryAddressId) REFERENCES Address(id)
);
CREATE TABLE OrderItems(
    orderId VARCHAR(36),
    itemId VARCHAR(36),
    itemCount INTEGER,
    FOREIGN KEY (orderId) REFERENCES Orders(id),
    FOREIGN KEY (itemId) REFERENCES Item(id),
    UNIQUE (orderId, itemId)
);
INSERT INTO Item
VALUES (
        "item1",
        "Apple",
        CONCAT(@STATIC_FILES_URL, "/assets/apple.svg"),
        0.5,
        "count",
        "Broccoli is a lovely green cruciferous vegetable. It’s healthy, delicious and nutritious, and there’s honestly nothing more you need to know.",
        "For maximum freshness, keep refrigerated. Wash before use.",
        "Produce of United Kingdom, Republic of Ireland, Germany, Italy, Netherlands, Poland, Spain, USA.",
        "Wash before use. Trim as required."
    ),
    (
        "item2",
        "Banana",
        CONCAT(@STATIC_FILES_URL, "/assets/banana.svg"),
        0.25,
        "count",
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Aliquam sunt sed alias dolor iure accusamus ab consequatur, ad, illum sapiente.",
        "Aliquam sunt sed alias dolor iure accusamus ab consequatur.",
        "Ullam iste dolore ut obcaecati ipsa? Similique maiores nostrum molestias rem? Deleniti asperiores nobis.",
        "Deleniti asperiores nobis velit aliquid."
    ),
    (
        "item3",
        "Strawberry",
        CONCAT(@STATIC_FILES_URL, "/assets/strawberry.svg"),
        0.56,
        "count",
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Aliquam sunt sed alias dolor iure accusamus ab consequatur, ad, illum sapiente.",
        "Aliquam sunt sed alias dolor iure accusamus ab consequatur.",
        "Ullam iste dolore ut obcaecati ipsa? Similique maiores nostrum molestias rem? Deleniti asperiores nobis.",
        "Deleniti asperiores nobis velit aliquid."
    ),
    (
        "item4",
        "Broccoli",
        CONCAT(@STATIC_FILES_URL, "/assets/broccoli.svg"),
        0.2,
        "head",
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Aliquam sunt sed alias dolor iure accusamus ab consequatur, ad, illum sapiente.",
        "Aliquam sunt sed alias dolor iure accusamus ab consequatur.",
        "Ullam iste dolore ut obcaecati ipsa? Similique maiores nostrum molestias rem? Deleniti asperiores nobis.",
        "Deleniti asperiores nobis velit aliquid."
    ),
    (
        "item5",
        "Kale",
        CONCAT(@STATIC_FILES_URL, "/assets/kale.svg"),
        0.2,
        "grams",
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Aliquam sunt sed alias dolor iure accusamus ab consequatur, ad, illum sapiente.",
        "Aliquam sunt sed alias dolor iure accusamus ab consequatur.",
        "Ullam iste dolore ut obcaecati ipsa? Similique maiores nostrum molestias rem? Deleniti asperiores nobis.",
        "Deleniti asperiores nobis velit aliquid."
    ),
    (
        "item6",
        "Lemon",
        CONCAT(@STATIC_FILES_URL, "/assets/lemon.svg"),
        0.8,
        "count",
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Aliquam sunt sed alias dolor iure accusamus ab consequatur, ad, illum sapiente.",
        "Aliquam sunt sed alias dolor iure accusamus ab consequatur.",
        "Ullam iste dolore ut obcaecati ipsa? Similique maiores nostrum molestias rem? Deleniti asperiores nobis.",
        "Deleniti asperiores nobis velit aliquid."
    ),
    (
        "item7",
        "Red Pepper",
        CONCAT(@STATIC_FILES_URL, "/assets/red-pepper.svg"),
        0.23,
        "count",
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Aliquam sunt sed alias dolor iure accusamus ab consequatur, ad, illum sapiente.",
        "Aliquam sunt sed alias dolor iure accusamus ab consequatur.",
        "Ullam iste dolore ut obcaecati ipsa? Similique maiores nostrum molestias rem? Deleniti asperiores nobis.",
        "Deleniti asperiores nobis velit aliquid."
    ),
    (
        "item8",
        "Orange",
        CONCAT(@STATIC_FILES_URL, "/assets/orange.svg"),
        0.9,
        "count",
        "Lorem ipsum dolor sit amet consectetur adipisicing elit. Aliquam sunt sed alias dolor iure accusamus ab consequatur, ad, illum sapiente.",
        "Aliquam sunt sed alias dolor iure accusamus ab consequatur.",
        "Ullam iste dolore ut obcaecati ipsa? Similique maiores nostrum molestias rem? Deleniti asperiores nobis.",
        "Deleniti asperiores nobis velit aliquid."
    );
INSERT INTO Recipe
VALUES(
        "recipe1",
        "Mixed Berry Melody",
        CONCAT(@STATIC_FILES_URL, "/assets/1.jfif"),
        "easy",
        20,
        5,
        250,
        455,
        10,
        20,
        5,
        2,
        20,
        15,
        "<p> Start by taking the 4 strawberries, chop them into tiny segments and introduce the strawberry. Check to make sure that the strawberries and the strawberry sit well together, before slicing and dicing a lemon and adding it to this rather strange combination of fruits. </p> <p> Peel the 2 cherries, if it’s even possible to peel a cherry, discard the stalks and place them neatly next to the other fruits. </p> <p> Finish off this imaginary recipe by sourcing a mint leaf, and place it perfectly in the center of the bowl, taking care not to upset the other fruits that have already been placed. </p>"
    ),
    (
        "recipe2",
        "Apple Tuna Salad",
        CONCAT(@STATIC_FILES_URL, "/assets/2.jfif"),
        "medium",
        15,
        10,
        350,
        500,
        20,
        38,
        9,
        4,
        26,
        10,
        "<p> Lorem ipsum dolor sit amet consectetur, adipisicing elit. At sapiente voluptates tenetur, esse impedit omnis tempora dolore deserunt architecto saepe nam in a pariatur. Labore nihil quis ea magnam rem? </p> <p> Lorem ipsum dolor sit amet consectetur adipisicing elit. Possimus ea alias </p> <p> Lorem, ipsum dolor sit amet consectetur adipisicing elit. Unde est cumque necessitatibus, ullam tenetur saepe cupiditate fugiat ad ut incidunt rem ipsa iste doloribus quos delectus consequuntur esse quia minus? Nam harum, eveniet eaque incidunt voluptatem facilis officiis, nostrum corporis sunt saepe, fuga debitis porro sed rerum obcaecati beatae alias adipisci. Facere omnis repellat reprehenderit dicta consequuntur quis repellendus placeat! </p>"
    ),
    (
        "recipe3",
        "Kale Crunch",
        CONCAT(@STATIC_FILES_URL, "/assets/3.jpg"),
        "easy",
        10,
        2,
        250,
        250,
        20,
        15,
        8,
        20,
        10,
        16,
        "<p> Lorem ipsum dolor sit amet consectetur, adipisicing elit. At sapiente voluptates tenetur, esse impedit omnis tempora dolore deserunt architecto saepe nam in a pariatur. Labore nihil quis ea magnam rem? </p> <p> Lorem ipsum dolor sit amet consectetur adipisicing elit. Possimus ea alias </p> <p> Lorem, ipsum dolor sit amet consectetur adipisicing elit. Unde est cumque necessitatibus, ullam tenetur saepe cupiditate fugiat ad ut incidunt rem ipsa iste doloribus quos delectus consequuntur esse quia minus? Nam harum, eveniet eaque incidunt voluptatem facilis officiis, nostrum corporis sunt saepe, fuga debitis porro sed rerum obcaecati beatae alias adipisci. Facere omnis repellat reprehenderit dicta consequuntur quis repellendus placeat! </p>"
    );
INSERT INTO RecipeIngredient
VALUES ("recipe1", "item1", 1),
    ("recipe1", "item3", 4),
    ("recipe1", "item6", 2),
    ("recipe2", "item1", 2),
    ("recipe2", "item7", 4),
    ("recipe3", "item5", 50),
    ("recipe3", "item6", 2);
-- SELECT *
-- FROM User;
-- SELECT *
-- FROM Item;
-- SELECT *
-- FROM Recipe;
-- SELECT *
-- FROM RecipeIngredient;
-- -- DROP TABLE User;
-- -- DROP TABLE Item;
-- SELECT Recipe.*,
--     COUNT(recipeingredient.ingredientId) as ingredientCount
-- FROM RECIPE
--     INNER JOIN RecipeIngredient ON Recipe.id = recipeingredient.recipeId
-- GROUP BY Recipe.id;
-- SELECT Item.*,
--     RecipeIngredient.ingredientItemCount
-- FROM Item
--     INNER JOIN RecipeIngredient ON RecipeIngredient.ingredientId = Item.id
-- WHERE RecipeIngredient.recipeId = "recipe1";
Select *
from Orders;
SELECT *
from Address;
SELECT *
FROM Orders
    INNER JOIN Address ON Orders.deliveryAddressId = Address.id