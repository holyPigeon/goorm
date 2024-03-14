import { createAsyncThunk, createSlice, PayloadAction } from "@reduxjs/toolkit";
import axios from "axios";
import { IProduct } from "../products/products.type";

type CartState = {
    products: IProduct[];
    totalPrice: number;
    userId: string;
}

const initialState: CartState = {
    products: localStorage.getItem("cartProducts") ?
        JSON.parse(localStorage.getItem("cartProducts") || "") : [],
    totalPrice: 0,
    userId: localStorage.getItem("userId") ?
        JSON.parse(localStorage.getItem("userId") || "") : "",
}

export const cartSlice = createSlice({
    name: "cart",
    initialState,
    reducers: {
     
    }
})

export default cartSlice.reducer;