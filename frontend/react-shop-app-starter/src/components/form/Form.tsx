import { FC } from 'react'


type FormProps = {
  title: string;
  getDataForm: (email: string, password: string) => void;
  firebaseError: string;
}

type Inputs = {
  email: string;
  password: string;
}

const Form: FC<FormProps> = ({ title, getDataForm, firebaseError }) => {

  return (
    <></>
  )
}

export default Form