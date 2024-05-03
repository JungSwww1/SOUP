'use client'

import Image from "next/image";
import {FuncTableColumnProps} from "@/types/functionDesc";
import * as styles from "./funcTableColumn.css";

export default function FuncTableColumn({funcDescData, handleCategory, categories, currClick,onChangeCategory}: FuncTableColumnProps) {


    return (
        <tr>
            <td>
                <span>
                    <input
                        onChange={(event)=>onChangeCategory(event.target.value,funcDescData.id)}
                        className={styles.category}
                        onClick={() => handleCategory(funcDescData.id)}
                        value={funcDescData.category}
                        style={{backgroundColor: funcDescData.color, width: `${funcDescData.category.length * 13}px`}}
                    />
                </span>
                {currClick === funcDescData.id &&
                    <div className={styles.btnGroup}>
                        <div className={`${styles.elementGroup}`}>
                        {categories.map(d =>
                                <button key={crypto.randomUUID()} type='button' className={styles.select}>{d}</button>)}
                        </div>
                    </div>
                }
            </td>
            <td>
                <input aria-label="input" type="text" value={funcDescData.functionName}
                       onChange={event => event.target.value}/>
            </td>
            <td>
                <input aria-label="input" type="text" value={funcDescData.description}
                       onChange={event => event.target.value}/>
            </td>
            <td>
                <input aria-label="input" type="text" value={funcDescData.point}
                       onChange={event => event.target.value}/>
            </td>
            <td>
                <input aria-label="input" type="text" value={funcDescData.priority}
                       onChange={event => event.target.value}/>
            </td>
            <td>
                <div className={styles.manager}>
                    <p>{funcDescData.manager.memberNickname}</p>
                    <Image unoptimized src={funcDescData.manager.memberProfileUri} alt="프로필 이미지" width={30}
                           height={30}/>
                </div>
            </td>
        </tr>);
}