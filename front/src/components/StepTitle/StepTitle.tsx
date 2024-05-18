'use client'

import InfoIcon from '@/../public/assets/icons/info'
import GuideModal from '@/components/GuideModal/GuideModal'
import { APIText, APITitle } from '@/components/GuideModal/text/APIGuide'
import * as styles from '@/components/StepTitle/stepTitle.css'
import { StepTitleProps } from '@/types/step'
import { useState } from 'react'
import { BuildText, BuildTitle } from '../GuideModal/text/BuildGuide'

export function StepTitle({ stepNum, title, desc, children }: StepTitleProps) {
  return (
    <section className={styles.container}>
      <div>
        <span className={styles.stepNum}>{`Step ${stepNum}.`}</span>
        <span className={styles.title}>{`${title}`}</span>
        <span className={styles.desc}>{` | ${desc}`}</span>
      </div>
      {children}
    </section>
  )
}

export function StepTitleWithGuide({
  stepNum,
  title,
  desc,
  guideTitle,
}: StepTitleProps) {
  const [guideContent, setGuideContent] = useState<{
    titleList: Array<string> | null
    textList: Array<string> | null
  }>({ titleList: null, textList: null })

  const handleModalClose = () => {
    setGuideContent({ titleList: null, textList: null })
  }

  const handleModalOpen = () => {
    if (guideContent.textList && guideContent.titleList) {
      handleModalClose()
      return
    }
    switch (guideTitle) {
      case '기획서 작성 가이드':
        // setGuideContent(<AiPlanGuide />)
        break
      case '기능 명세서 작성 가이드':
        // setGuideContent(<FuncGuide />)
        break
      case '플로우 차트 작성 가이드':
        // setGuideContent(<FlowChartGuide />)
        break
      case 'API 명세서 작성 가이드':
        setGuideContent({ titleList: APITitle, textList: APIText })
        break
      case '빌드 가이드':
        setGuideContent({ titleList: BuildTitle, textList: BuildText })
        break
      default:
        setGuideContent({ titleList: null, textList: null })
    }
  }

  return (
    <StepTitle stepNum={stepNum} title={title} desc={desc}>
      <button
        type="button"
        className={styles.guideTitle}
        onClick={handleModalOpen}
      >
        <InfoIcon color="#535252" />
        {guideTitle}
      </button>
      {guideContent.textList && guideContent.titleList ? (
        <GuideModal
          onClose={handleModalClose}
          textList={guideContent.textList}
          titleList={guideContent.titleList}
        />
      ) : null}
    </StepTitle>
  )
}
