import {StepTitle} from '@/components/StepTitle/StepTitle'
import ERDDrawing from '@/containers/erd/ERDDrawing'
import * as styles from '@/containers/erd/erd.css'
import Room from "@/app/(after-auth)/project/[projectId]/erd/Room";
import Live from "@/components/cursor/Live";

type Props = {
    params: { projectId: string },
}
export default function ERD({params}:Props) {
    const {projectId} = params;
    return (
        <Room>
            <Live>
                <StepTitle
                    stepNum={5}
                    title="ERD"
                    desc="데이터베이스를 설계하세요"
                />
                <div className={styles.container}>
                    <ERDDrawing projectId={projectId}/>
                </div>
            </Live>
        </Room>
    )
}
