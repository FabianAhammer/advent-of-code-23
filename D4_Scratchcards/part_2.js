const testInput =
    `Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11`;
import part_1_data from "./test_data.js";


printTest(part_1_data);


function printTest(inp) {
    const lines = inp.split("\n");
    const scratchCopies = lines.map(e => {
        return {
            name: e.split(":")[0],
            copies: 1,
            winnings: e.split(":")[1].split("|")[0].split(" ").filter(e => e.trim() !== ""),
            candidates: e.split(":")[1].split("|")[1].split(" ").filter(e => e.trim() !== "")
        };
    })
    const result = scratchCopies.map((e, i) => getAmountOfCopies(e, scratchCopies, i))
        .reduce((a, b) => a + b, 0);
    console.log(scratchCopies);
    console.log(result);

}


//41 92 73 84 69 | 59 84 76 51 58  5 54 83
function getAmountOfCopies(obj, dataArr, currIdx) {
    const winnings = obj.winnings;
    const canidates = obj.candidates;
    const allowed = canidates.filter(e => winnings.includes(e));
    const multiplierForFound = allowed.reduce((a, b) => a + b, 0);
    if (allowed) {
        const ceiling = Math.min(dataArr.length - 1, currIdx + allowed.length);
        for (let i = currIdx + 1; i <= ceiling; i++) {
            // console.log(i);
            if (dataArr[i]?.copies) {
                dataArr[i].copies += obj.copies;
            }
        }
    }
    return obj.copies;
}