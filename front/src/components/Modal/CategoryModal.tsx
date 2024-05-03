'use client'

import {useState} from "react";
import * as styles from "./CategoryModal.css";

type Props = {
    index: number;
    datas: string[];
}
export default function CategoryModal({index, datas}: Props) {
    const [currClick, setCurrClick] = useState<boolean>(false);
    const handleCategoryClick = () => {
        if (!currClick) setCurrClick(true);
        if (currClick) setCurrClick(false);

    };
    return (
        <div className={styles.btnGroup}>
            <button
                type="button"
                className={styles.category}
                data-toggle="dropdown"
                onClick={() => handleCategoryClick()}
            >
                {datas[index]}
            </button>
            <div className={`${styles.elementGroup} ${currClick ? "" : styles.hidden}`}>
                <div>
                    <p>asdf</p>
                    <p>asdf</p>
                    <p>asdf</p>
                </div>
            </div>
        </div>

    )
}