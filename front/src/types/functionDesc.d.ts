import {MemberRes} from "@/types/member";

export interface FuncDescRes {
    id: string;
    category: string;
    functionName: string;
    description: string;
    point: number;
    priority: string;
    manager: MemberRes;


}

export interface FuncDescResWithColor extends FuncDescRes {
    color: string
}

export type FuncTableColumnProps = {
    funcDescData: FuncDescResWithColor;
    categories: string[];
    handleCategory: (id: string) => void;
    currClick: string;
    onChangeCategory: (value: string, id: string) => void;
}