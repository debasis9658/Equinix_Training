let parent = document.querySelector("#__next > div.overflow-hidden.w-full.h-full.relative.flex.z-0 > div.relative.flex.h-full.max-w-full.flex-1.overflow-hidden > div > main > div.flex-1.overflow-hidden > div > div > div > div.text-gray-800.w-full.mx-auto.md\\:max-w-2xl.lg\\:max-w-3xl.md\\:flex.md\\:flex-col.px-6.dark\\:text-gray-100 > div");
let child = document.querySelector("#__next > div.overflow-hidden.w-full.h-full.relative.flex.z-0 > div.relative.flex.h-full.max-w-full.flex-1.overflow-hidden > div > main > div.flex-1.overflow-hidden > div > div > div > div.text-gray-800.w-full.mx-auto.md\\:max-w-2xl.lg\\:max-w-3xl.md\\:flex.md\\:flex-col.px-6.dark\\:text-gray-100 > div > div:nth-child(1)");

let new_child = document.createElement("div");
let new_para = document.createElement("p");

new_para.textContent = "This is a new section";
new_child.appendChild(new_para);
console.log(new_child);
parent.appendChild(new_child);