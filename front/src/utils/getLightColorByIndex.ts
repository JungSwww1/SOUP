const colors: string[] = ['#F5A9A9', '#F3E2A9', '#F5A9E1','#F5D0A9', '#F5BCA9',  '#F2F5A9', '#E1F5A9', '#D0F5A9', '#BCF5A9', '#A9F5F2', '#A9E2F3', '#81BEF7', '#819FF7', '#9F81F7',  '#E6E6E6'];

const getLightColorByIndex = (index: number) => colors[index % colors.length];

export default getLightColorByIndex;

