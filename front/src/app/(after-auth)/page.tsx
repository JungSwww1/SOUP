'use client'

import Header from '@/components/Header/Header'
import Project from '@/containers/Project'
import * as styles from '@/containers/page.css'
import {ProjectRes} from '@/containers/project/types/project'
import {createProject, getProjectList} from "@/apis/project/projectAPI";
import {useEffect, useState} from "react";
import {useRouter} from "next/navigation";
import Loading from "@/app/loading";
import {getMemberInfo} from "@/apis/member/memberAPI";
import {MemberRes} from "@/containers/project/types/member";
import useMemberStore from "@/stores/useMemberStore";


export default function AfterAuth() {
    const [projects, setProjects] = useState<ProjectRes[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const router = useRouter();
    const {setMe} = useMemberStore();
    useEffect(() => {
        async function fetchData() {
            try {
                const data = await getProjectList();
                setProjects(data);
            } catch (error) {
                // console.error('Error fetching project list:', error);
            } finally {
                setLoading(false);
            }
        }
        fetchData();
        getMemberInfo().then(data=>{
            const { memberId, ...tempWithoutMemberId } = data.result;
            const temp: MemberRes = {
                ...tempWithoutMemberId,
                id: memberId
            };
            setMe(temp);
        })
    }, [setMe]);

    const handleCreateProject = async () => {
        setLoading(true);
        try {
            const data = await createProject();
            router.push(`/project/${data}/outline`);
        } catch (error) {
            // console.error('Error creating project:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className={styles.container}>
            {loading && <div className={styles.loading}><Loading/></div>}
            <Header theme="black" useVoice={false}/>
            <div className={styles.content}>
                <div>
                    <p>내 프로젝트</p>
                    <button type='button' onClick={handleCreateProject}>+ 새 프로젝트</button>
                </div>
                    <div className={styles.projects}>
                        {projects.map(project => (
                            <Project key={project.id} {...project} />
                        ))}
                    </div>

            </div>
        </div>
    )
}
