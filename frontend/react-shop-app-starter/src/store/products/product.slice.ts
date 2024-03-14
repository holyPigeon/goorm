import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { IProduct } from "./products.type";

type ProductType = {
    product: IProduct;
    isLoading: boolean;
    error: string;
}

const initialState: ProductType = {
    product: {} as IProduct,
    isLoading: false,
    error: ""
}

export const productSlice = createSlice({
    name: 'product',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
     
    }
})

export default productSlice.reducer;