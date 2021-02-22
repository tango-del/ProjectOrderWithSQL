create database if not exists shop;

use shop;

# drop database shop;

create table orders
(
	id int auto_increment,
	user_id int not null,
	status varchar(15) default 'open' null,
	created_at timestamp default CURRENT_TIMESTAMP null,
	constraint orders_pk
		primary key (id)
);

create table products
(
	id int auto_increment,
	name varchar(50) not null,
	price int not null,
	status enum('out_of_stock', 'in_stock', 'running_low') not null,
	created_at timestamp default CURRENT_TIMESTAMP null,
	isDeleted boolean default 0 not null,
	constraint products_pk
		primary key (id)
);

create unique index products_name_uindex
	on products (name);

create table order_items
(
    id int auto_increment,
	order_id int null,
	product_id int null,
	quantity int null,
	constraint order_items_pk
		primary key (id)
);

alter table order_items
	add constraint order_items_id_fk
		foreign key (order_id) references orders (id);

alter table order_items
	add constraint order_items_pr_id_fk
		foreign key (product_id) references products (id);


/*---------------Product Entities---------------*/
insert into products (name, price, status) values ('Sauce - Sesame Thai Dressing', 96, 'running_low');
insert into products (name, price, status) values ('Cookie - Oatmeal', 117, 'in_stock');
insert into products (name, price, status) values ('Quail - Eggs, Fresh', 130, 'running_low');
insert into products (name, price, status) values ('Gatorade - Fruit Punch', 85, 'running_low');
insert into products (name, price, status) values ('Bag - Regular Kraft 20 Lb', 103, 'out_of_stock');
insert into products (name, price, status) values ('Fireball Whisky', 121, 'out_of_stock');
insert into products (name, price, status) values ('Bread - Roll, Soft White Round', 88, 'out_of_stock');
insert into products (name, price, status) values ('Puree - Strawberry', 53, 'out_of_stock');
insert into products (name, price, status) values ('Cognac - Courvaisier', 37, 'out_of_stock');
insert into products (name, price, status) values ('Pate - Peppercorn', 144, 'in_stock');
insert into products (name, price, status) values ('Cut Wakame - Hanawakaba', 55, 'in_stock');
insert into products (name, price, status) values ('Appetizer - Veg Assortment', 75, 'running_low');
insert into products (name, price, status) values ('Oil - Margarine', 126, 'in_stock');
insert into products (name, price, status) values ('Napkin Colour', 42, 'out_of_stock');
insert into products (name, price, status) values ('Pastry - Banana Tea Loaf', 16, 'running_low');
insert into products (name, price, status) values ('Table Cloth 81x81 Colour', 56, 'running_low');
insert into products (name, price, status) values ('Grapes - Red', 148, 'out_of_stock');
insert into products (name, price, status) values ('Red Cod Fillets - 225g', 15, 'in_stock');
insert into products (name, price, status) values ('Onions - Cooking', 78, 'running_low');
insert into products (name, price, status) values ('Lid - Translucent, 3.5 And 6 Oz', 104, 'out_of_stock');
insert into products (name, price, status) values ('Milk - Chocolate 250 Ml', 85, 'running_low');
insert into products (name, price, status) values ('Neckerchief Blck', 18, 'in_stock');
insert into products (name, price, status) values ('Basil - Seedlings Cookstown', 24, 'running_low');
insert into products (name, price, status) values ('Garam Masala Powder', 119, 'out_of_stock');
insert into products (name, price, status) values ('Trout - Rainbow, Frozen', 144, 'in_stock');
insert into products (name, price, status) values ('Pork - Back, Short Cut, Boneless', 117, 'out_of_stock');
insert into products (name, price, status) values ('Wine - Saint - Bris 2002, Sauv', 101, 'out_of_stock');
insert into products (name, price, status) values ('Tart Shells - Sweet, 2', 145, 'in_stock');
insert into products (name, price, status) values ('Pumpkin', 85, 'in_stock');
insert into products (name, price, status) values ('Crab - Soft Shell', 112, 'in_stock');
insert into products (name, price, status) values ('Macaroons - Two Bite Choc', 73, 'running_low');
insert into products (name, price, status) values ('Vinegar - Rice', 107, 'out_of_stock');
insert into products (name, price, status) values ('Yogurt - Assorted Pack', 145, 'out_of_stock');
insert into products (name, price, status) values ('Bread - Bagels, Plain', 117, 'running_low');
insert into products (name, price, status) values ('Five Alive Citrus', 29, 'out_of_stock');
insert into products (name, price, status) values ('Sugar - Brown', 128, 'in_stock');
insert into products (name, price, status) values ('Food Colouring - Orange', 105, 'in_stock');
insert into products (name, price, status) values ('Bread - Roll, Italian', 105, 'out_of_stock');
insert into products (name, price, status) values ('Dc - Sakura Fu', 58, 'running_low');
insert into products (name, price, status) values ('Ranchero - Primerba, Paste', 147, 'out_of_stock');
insert into products (name, price, status) values ('Nut - Pumpkin Seeds', 123, 'running_low');
insert into products (name, price, status) values ('Ecolab - Hobart Washarm End Cap', 140, 'out_of_stock');
insert into products (name, price, status) values ('Wine - White, Riesling, Semi - Dry', 39, 'in_stock');
insert into products (name, price, status) values ('Yogurt - Plain', 33, 'out_of_stock');
insert into products (name, price, status) values ('Sauce - Soya, Dark', 107, 'running_low');
insert into products (name, price, status) values ('Radish - Pickled', 86, 'running_low');
insert into products (name, price, status) values ('Cheese - St. Paulin', 107, 'running_low');
insert into products (name, price, status) values ('Island Oasis - Lemonade', 20, 'out_of_stock');
insert into products (name, price, status) values ('Coffee Guatemala Dark', 124, 'out_of_stock');
insert into products (name, price, status) values ('Muffin Mix - Blueberry', 84, 'out_of_stock');
insert into products (name, price, status) values ('Cheese - St. Andre', 115, 'out_of_stock');
insert into products (name, price, status) values ('Pepper - Gypsy Pepper', 105, 'out_of_stock');
insert into products (name, price, status) values ('Beer - Alexander Kieths, Pale Ale', 89, 'running_low');
insert into products (name, price, status) values ('Oil - Olive', 149, 'running_low');
insert into products (name, price, status) values ('Sugar - Monocystal / Rock', 37, 'out_of_stock');
insert into products (name, price, status) values ('Bread - Hamburger Buns', 141, 'in_stock');
insert into products (name, price, status) values ('Soup - Canadian Pea, Dry Mix', 36, 'out_of_stock');
insert into products (name, price, status) values ('Food Colouring - Green', 34, 'in_stock');
insert into products (name, price, status) values ('Island Oasis - Mango Daiquiri', 40, 'out_of_stock');
insert into products (name, price, status) values ('Bread - Raisin Walnut Oval', 24, 'in_stock');
insert into products (name, price, status) values ('Juice - Grape, White', 90, 'in_stock');
insert into products (name, price, status) values ('Table Cloth 54x54 White', 133, 'out_of_stock');
insert into products (name, price, status) values ('Sauce - Demi Glace', 93, 'in_stock');
insert into products (name, price, status) values ('Liqueur Banana, Ramazzotti', 115, 'running_low');
insert into products (name, price, status) values ('Straws - Cocktale', 35, 'running_low');
insert into products (name, price, status) values ('Nantucket - Carrot Orange', 33, 'in_stock');
insert into products (name, price, status) values ('Cardamon Seed / Pod', 103, 'in_stock');
insert into products (name, price, status) values ('Cheese - Oka', 134, 'out_of_stock');
insert into products (name, price, status) values ('Flour - Chickpea', 121, 'in_stock');
insert into products (name, price, status) values ('Iced Tea - Lemon, 460 Ml', 69, 'running_low');




/*---------------Order Entities---------------*/
insert into orders (user_id) values (17);
insert into orders (user_id) values (11);
insert into orders (user_id) values (8);
insert into orders (user_id) values (26);
insert into orders (user_id) values (27);
insert into orders (user_id) values (28);
insert into orders (user_id) values (25);
insert into orders (user_id) values (13);
insert into orders (user_id) values (22);
insert into orders (user_id) values (17);
insert into orders (user_id) values (16);
insert into orders (user_id) values (24);
insert into orders (user_id) values (10);
insert into orders (user_id) values (28);
insert into orders (user_id) values (29);
insert into orders (user_id) values (19);
insert into orders (user_id) values (12);
insert into orders (user_id) values (23);
insert into orders (user_id) values (21);
insert into orders (user_id) values (12);
insert into orders (user_id) values (10);
insert into orders (user_id) values (22);
insert into orders (user_id) values (16);
insert into orders (user_id) values (2);
insert into orders (user_id) values (28);
insert into orders (user_id) values (25);
insert into orders (user_id) values (30);
insert into orders (user_id) values (24);
insert into orders (user_id) values (23);
insert into orders (user_id) values (1);
insert into orders (user_id) values (3);
insert into orders (user_id) values (27);
insert into orders (user_id) values (15);
insert into orders (user_id) values (18);
insert into orders (user_id) values (30);
insert into orders (user_id) values (28);
insert into orders (user_id) values (23);
insert into orders (user_id) values (1);
insert into orders (user_id) values (16);
insert into orders (user_id) values (27);


/*---------------Order_Items Entities---------------*/
insert into order_items (order_id, product_id, quantity) values (6, 15, 14);
insert into order_items (order_id, product_id, quantity) values (30, 64, 21);
insert into order_items (order_id, product_id, quantity) values (26, 46, 20);
insert into order_items (order_id, product_id, quantity) values (22, 7, 8);
insert into order_items (order_id, product_id, quantity) values (26, 2, 24);
insert into order_items (order_id, product_id, quantity) values (22, 17, 7);
insert into order_items (order_id, product_id, quantity) values (18, 10, 15);
insert into order_items (order_id, product_id, quantity) values (14, 27, 18);
insert into order_items (order_id, product_id, quantity) values (8, 45, 2);
insert into order_items (order_id, product_id, quantity) values (37, 18, 10);
insert into order_items (order_id, product_id, quantity) values (27, 31, 15);
insert into order_items (order_id, product_id, quantity) values (24, 63, 18);
insert into order_items (order_id, product_id, quantity) values (29, 16, 10);
insert into order_items (order_id, product_id, quantity) values (23, 43, 24);
insert into order_items (order_id, product_id, quantity) values (32, 47, 12);
insert into order_items (order_id, product_id, quantity) values (13, 32, 4);
insert into order_items (order_id, product_id, quantity) values (24, 12, 21);
insert into order_items (order_id, product_id, quantity) values (21, 35, 5);
insert into order_items (order_id, product_id, quantity) values (34, 13, 2);
insert into order_items (order_id, product_id, quantity) values (37, 31, 26);
insert into order_items (order_id, product_id, quantity) values (9, 29, 1);
insert into order_items (order_id, product_id, quantity) values (4, 70, 15);
insert into order_items (order_id, product_id, quantity) values (32, 63, 23);
insert into order_items (order_id, product_id, quantity) values (20, 35, 18);
insert into order_items (order_id, product_id, quantity) values (21, 55, 4);
insert into order_items (order_id, product_id, quantity) values (7, 31, 19);
insert into order_items (order_id, product_id, quantity) values (8, 40, 9);
insert into order_items (order_id, product_id, quantity) values (12, 19, 8);
insert into order_items (order_id, product_id, quantity) values (25, 58, 24);
insert into order_items (order_id, product_id, quantity) values (13, 25, 16);
insert into order_items (order_id, product_id, quantity) values (33, 47, 12);
insert into order_items (order_id, product_id, quantity) values (29, 51, 6);
insert into order_items (order_id, product_id, quantity) values (37, 68, 11);
insert into order_items (order_id, product_id, quantity) values (35, 53, 10);
insert into order_items (order_id, product_id, quantity) values (11, 26, 29);
insert into order_items (order_id, product_id, quantity) values (10, 12, 29);
insert into order_items (order_id, product_id, quantity) values (20, 50, 23);
insert into order_items (order_id, product_id, quantity) values (20, 1, 25);
insert into order_items (order_id, product_id, quantity) values (15, 34, 23);
insert into order_items (order_id, product_id, quantity) values (7, 3, 27);
insert into order_items (order_id, product_id, quantity) values (37, 29, 23);
insert into order_items (order_id, product_id, quantity) values (32, 19, 8);
insert into order_items (order_id, product_id, quantity) values (26, 57, 12);
insert into order_items (order_id, product_id, quantity) values (10, 46, 17);
insert into order_items (order_id, product_id, quantity) values (36, 38, 11);
insert into order_items (order_id, product_id, quantity) values (12, 32, 14);
insert into order_items (order_id, product_id, quantity) values (24, 16, 9);
insert into order_items (order_id, product_id, quantity) values (27, 47, 2);
insert into order_items (order_id, product_id, quantity) values (37, 62, 4);
insert into order_items (order_id, product_id, quantity) values (10, 53, 9);
insert into order_items (order_id, product_id, quantity) values (36, 64, 24);
insert into order_items (order_id, product_id, quantity) values (2, 54, 27);
insert into order_items (order_id, product_id, quantity) values (17, 40, 19);
insert into order_items (order_id, product_id, quantity) values (12, 4, 13);
insert into order_items (order_id, product_id, quantity) values (34, 46, 14);
insert into order_items (order_id, product_id, quantity) values (37, 53, 15);
insert into order_items (order_id, product_id, quantity) values (24, 58, 19);
insert into order_items (order_id, product_id, quantity) values (40, 50, 21);
insert into order_items (order_id, product_id, quantity) values (2, 4, 17);
insert into order_items (order_id, product_id, quantity) values (29, 20, 10);
	