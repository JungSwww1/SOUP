import * as styles from '@/components/Table/table.css'
import { TableProps } from '@/types/table'

/**
 * ## 공통 Table Component입니다.
 * @param TableProps
 * headers - 테이블 (TableHead[])
 *
 * hasNewLine - 테이블 가장 아래에 추가하는 항목 있는지 여부 (boolean)
 *
 * children - 테이블 data (React.ReactNode)
 *
 */
export default function Table({ headers, hasNewLine, children }: TableProps) {
  return (
    <table className={styles.table}>
      <thead>
        <tr>
          {headers.map((item, k) => (
            <th colSpan={item.colSpan ?? 1} key={k}>
              {item.title}
              <span
                className={styles.essential}
                style={
                  item.isEssential ? { display: 'inline' } : { display: 'none' }
                }
              >
                *
              </span>
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {children}
        {hasNewLine ? (
          <tr>
            <td
              className={styles.newLine}
              colSpan={headers.reduce(
                (result, item) =>
                  result + (item.colSpan ? item.colSpan - 1 : 0),
                headers.length,
              )}
            >
              + 새로 만들기
            </td>
          </tr>
        ) : null}
      </tbody>
    </table>
  )
}
