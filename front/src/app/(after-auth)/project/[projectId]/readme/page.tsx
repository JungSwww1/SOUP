import dynamic from 'next/dynamic'
import {StepTitle} from '@/components/StepTitle/StepTitle'
import Room from "@/app/(after-auth)/project/[projectId]/readme/Room";
import Live from "@/components/cursor/Live";

export default function ReadMe() {
    const ToastEditor = dynamic(() => import('@/containers/readme/ToastEditor'), {
        ssr: false,
    });
    return (
        <Room>
            <Live>
                <StepTitle
                    stepNum={8}
                    title="README"
                    desc="프로젝트 기획, 설계 부분의 REDAME.md를 작성해보세요."
                />
                <ToastEditor/>
            </Live>
        </Room>)
}