'use client'

import {useEffect, useState} from 'react';
import {useRecoilState} from "recoil";
import {FuncDescRes, FuncDescResWithColor} from "@/types/functionDesc";
import useFunctionState from "@/stores/useFunctionState";
import FuncTableColumn from "@/containers/func/FuncTableColumn/FuncTableColumn";
import {faker} from "@faker-js/faker";
import getLightColorByIndex from "@/utils/getLightColorByIndex";
import * as styles from './funcTable.css';

const Member = [
    {
        memberId: "1",
        memberProfileUri: faker.image.avatarGitHub(),
        memberNickname: '최지우',
    },
    {
        memberId: "2",
        memberProfileUri: faker.image.avatarGitHub(),
        memberNickname: '정승원',
    },
];

const DummyData: FuncDescRes[] = [
    {
        id: "1",
        category: '회원관리',
        functionName: '소셜 로그인',
        description: '구글 소셜로그인을 적용합니다.',
        point: 3,
        priority: '넘높음',
        manager: Member[0],
    },
    {
        id: "2",
        category: '게시물 등록',
        functionName: '카테고리 검색',
        description: '사용자가 입력한 제목 기반',
        point: 3,
        priority: '넘높음',
        manager: Member[1],
    },
    {
        id: "3",
        category: '게시물 등록',
        functionName: '카테고리 검색',
        description: '사용자가 입력한 제목 기반',
        point: 3,
        priority: '넘높음',
        manager: Member[1],
    },
];
export default function FuncTable() {
    const [funcDescData, setFuncDescData] = useRecoilState<FuncDescResWithColor[]>(useFunctionState);
    const [currClick, setCurrClick] = useState<string>("-1");

    useEffect(() => {
        const categories = Array.from(new Set(DummyData.map((data) => data.category)));
        const categoryColorMap = new Map();
        categories.forEach((category, index) => {
            const color = getLightColorByIndex(index);
            categoryColorMap.set(category, color);
        });
        const updatedData = DummyData.map((data) => ({
            ...data,
            color: categoryColorMap.get(data.category)
        }));
        setFuncDescData(updatedData);
    }, [setFuncDescData]);

    const handleCategory = (id: string) => {
        if (currClick !== id || currClick === id) setCurrClick(id);
    };

    const onChangeCategory = (value: string, id: string) => {
        const updatedFuncDescData = funcDescData.map(item => {
            if (item.id === id) {
                return { ...item, category: value };
            }
            return item;
        });
        setFuncDescData(updatedFuncDescData);
    };

    return (
        <div className={styles.container}>
            {currClick !== "-1" && (
                <div
                    onClick={() => handleCategory("-1")}
                    onKeyDown={(e) => {
                        if (e.key === 'Enter' || e.key === ' ') {
                            handleCategory("-1");
                        }
                    }}
                    role="presentation"
                    className={styles.clickBackground}
                />
            )}
            <table className={styles.table}>
                <thead>
                <tr>
                    <th>카테고리</th>
                    <th>기능명</th>
                    <th>설명</th>
                    <th>포인트</th>
                    <th>우선순위</th>
                    <th>담당자</th>
                </tr>
                </thead>
                <tbody>
                {funcDescData.map((item, index) => (
                    <FuncTableColumn
                        key={index}
                        funcDescData={item}
                        categories={Array.from(new Set(funcDescData.map((data) => data.category)))}
                        handleCategory={handleCategory}
                        currClick={currClick}
                        onChangeCategory={(value) => onChangeCategory(value, item.id)}
                    />
                ))}
                <tr className={styles.createNew}>
                    <td colSpan={6}>
                        <button type="button">+ 새로 만들기</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    );

}
