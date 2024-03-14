import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { IProduct } from "./products.type";



type ProductsType = {
    products: IProduct[];
    isLoading: boolean;
    error: string;
}

const initialState: ProductsType = {
    products: [],
    isLoading: false,
    error: "",
}

export const productsSlice = createSlice({
    name: 'products',
    initialState,
    reducers: {},
    // reducer를 추가하면 프로미스의 진행 상태에 따라서 리듀서를 실행할 수 있습니다.
    extraReducers: (builder) => {
       
    }
})


export default productsSlice.reducer;