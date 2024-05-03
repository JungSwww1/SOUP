import { atom } from 'recoil';
import {FuncDescResWithColor} from "@/types/functionDesc";

const useFunctionState = atom<FuncDescResWithColor[]>({
    key: 'useFunctionState',
    default: [],
});

export default useFunctionState;