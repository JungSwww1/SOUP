import Image from "next/image"
import closeIcon from "#/assets/icons/modals/closeIcon.svg"
import * as styles from "@/containers/outline/styles/modals/outlineModal.css"
import { ChangeEvent, KeyboardEvent, useState } from 'react'
import { inviteMemberAPI } from '@/apis/outline/outlineAPI'
import { LiveObject } from '@liveblocks/client'
import { ProjectMember } from '@/containers/outline/types/outlineStorage'
import { Toast } from '@/utils/toast'
import { useParams } from 'next/navigation'
import { TextField } from "@mui/material"
import { useStyles } from '@/containers/outline/styles/outlineMUI'
import { useMutation, useStorage } from '../../../../../../liveblocks.config'

function OutlineTeamModal (props: { clickModal: () => void }) {
  const { clickModal } = props;
  const {projectId} = useParams()
  const [auth, setAuth] =useState<string>('DEVELOPER')
  const [nameInput, setNameInput] = useState<string>('');
  const [roleInput, setRoleInput] = useState<string>('');
  const [emailInput, setEmailInput] = useState<string>('')
  const [tags, setTags] = useState<Array<{ id: string, role_name: string }>>([]);
  const invitedTeam = useStorage((root)=>root.outline)
  const [isComposing, setIsComposing] = useState(false);

  const handleComposition = (e: React.CompositionEvent) => {
    setIsComposing(e.type !== 'compositionend')
  };

  const updateTeam = useMutation(({ storage }, data) => {
    const members = storage.get("outline")?.get("project_team")
    const index = members?.findIndex(member => member.get("email") === data.email)
    if( index !== undefined && index !== -1) {
      members?.delete(index)
    }
    members?.push(new LiveObject<ProjectMember>(data))
  }, []);

  const handleOuterClick = (e: React.MouseEvent) => {
    if (e.target === e.currentTarget) {
      clickModal();
    }
  };

  const addHashtag = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter' && !isComposing && roleInput.trim() !== '') {
      const newTag = { id: crypto.randomUUID(), role_name: roleInput };
      setTags(prevTags => [...prevTags, newTag]);
      setRoleInput('');
      e.preventDefault();
    }
  }
  const removeTag = (tagId: string) => {
    setTags(prevTags => prevTags.filter(tag => tag.id !== tagId));
  }

  const changeInput = (setter: React.Dispatch<React.SetStateAction<string>>) =>
    (e: ChangeEvent<HTMLInputElement>) => { setter(e.target.value) }
  const changeAuth = (e: ChangeEvent<HTMLSelectElement>) => {
    const selectedAuth = e.target.value === 'ADMIN' ? 'ADMIN' : 'DEVELOPER';
    setAuth(selectedAuth);
  };

  const invite = () => {
    if(nameInput && emailInput) {
      updateTeam({id:crypto.randomUUID(), name:nameInput, email:emailInput, roles:tags})
      inviteMemberAPI(`${projectId}`,{email:emailInput,roles:[`${auth}`]})
        .then(response=> {
          if(response.status === 201) {
            Toast.success('초대 완료했습니다.')
            setTags([]);
            setEmailInput('');
            setNameInput('');
          }
        }).catch(error=>Toast.error(`초대하기 실패: ${error.message}`))
    } else if(nameInput) {
        Toast.error('이메일을 입력하세요')
    } else {
        Toast.error('이름을 입력하세요')
    }
  }

  return (
    <div role="presentation" className={styles.modalContainer} onClick={handleOuterClick}>
      <div className={styles.modalSubContainer}>
        {/* 팀원 초대하기 / X버튼 */}
        <div className={styles.topDivision}>
          <p className={styles.topSubTitle}>팀원 초대하기</p>
          <div role="presentation" className={styles.topSubXDiv} onClick={()=>clickModal()} >
            <Image src={closeIcon} width={33} height={33} alt="X"/>
          </div>
        </div>
        <hr/>
        {/* 역할, 이메일, 초대하기 */}
        <div className={styles.middleContainer}>

          <div className={styles.middleDiv}>
            <div className={styles.mainRoleDiv}>
              <div className={styles.middleRoleDiv}>
                <TextField className={useStyles().role}
                           label="엔터로 역할 추가"
                           value={roleInput}
                           onChange={changeInput(setRoleInput)}
                           onKeyDown={addHashtag}
                           onCompositionStart={handleComposition}
                           onCompositionEnd={handleComposition}
                />
              </div>
              <div className={styles.roleShow}>
                {tags.map(tag => (
                  <div key={tag.id} className={styles.role}>
                    {tag.role_name}
                    <button type="button" className={styles.deleteButton} onClick={() => removeTag(tag.id)}>✖</button>
                  </div>
                ))}
              </div>
            </div>
            <div className={styles.mainInviteDiv}>
              <div className={styles.middleSubDiv}>
                <div>JIRA 권한</div>
                <select style={{ borderRadius: '8px' }} onChange={changeAuth}>
                  <option value="DEVELOPER">No</option>
                  <option value="ADMIN">Yes</option>
                </select>
              </div>
              <div className={styles.middleNameDiv}>
                <TextField label="이름" className={useStyles().teamModalRole}
                       value={nameInput}
                       onChange={changeInput(setNameInput)}
                />
              </div>
              <div className={styles.middleEmailDiv}>
                <TextField label="e-mail 주소" className={useStyles().teamModalRole}
                       value={emailInput}
                       onChange={changeInput(setEmailInput)}
                />
              </div>
              <button type="button" className={styles.invite} onClick={invite}>초대하기</button>
            </div>
          </div>

          {/* 초대된 팀원 */}
          <p className={styles.nowTeamTitle}>현재 초대된 팀원</p>

          {invitedTeam?.project_team.toReversed().map(member => (
          <div key={member.id} className={styles.bottomDivision}>
            <div className={styles.bottomProfile}>
              <div className={styles.profileDetail}>
                <p style={{fontWeight:'bold'}}>{member.name}</p>
                <p>{member.email}</p>
              </div>
            </div>

            <div className={styles.roleSubDiv}>
            {member?.roles.map((role, index) => (
              <div key={`${role.role_id}-${index}`} className={styles.invitedRole}>{role.role_name}</div>
            ))}
            </div>
          </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default OutlineTeamModal;
