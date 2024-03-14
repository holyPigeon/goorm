import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./user/user.slice";
import categoriesReducer from './categories/categories.slice';
import productsReducer from './products/products.slice';
import productReducer from './products/product.slice';
import cartReducer from './cart/cart.slice';
import { useSelector } from "react-redux";

export const store = configureStore({
    reducer: {

    }
})


export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch;
