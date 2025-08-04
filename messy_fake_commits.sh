#!/bin/bash
#
# messy_fake_commits.sh
# backdate commits 2025-04-24 → 2025-07-14, high activity early, break week, random skips
#

FILE="progress.log"
START_DATE="2025-04-24"
END_DATE="2025-07-14"
HIGH_END="2025-05-17"
BREAK_START="2025-06-07"
BREAK_END="2025-06-13"

start=$(date -d "$START_DATE" +%s)
end=$(date -d "$END_DATE"   +%s)

echo "" > "$FILE"

while [ $start -le $end ]; do
  day=$(date -d "@$start" +%Y-%m-%d)
  dow=$(date -d "$day" +%u)

  # skip weekends
  if [ $dow -gt 5 ]; then
    start=$((start + 86400)); continue
  fi

  # skip break week
  if [[ "$day" > "$BREAK_START" && "$day" < "$BREAK_END" ]]; then
    start=$((start + 86400)); continue
  fi

  # ~20% random skip
  if (( RANDOM % 5 == 0 )); then
    start=$((start + 86400)); continue
  fi

  # choose commits/day
  if [[ "$day" < "$HIGH_END" ]]; then
    count=$((RANDOM % 4 + 2))   # 2–5
  else
    count=$((RANDOM % 3))       # 0–2
  fi

  for ((i=0; i<count; i++)); do
    h=$((RANDOM % 14 + 9))
    m=$((RANDOM % 60))
    s=$((RANDOM % 60))
    ts="$day $(printf "%02d:%02d:%02d" $h $m $s)"

    echo "Work at $ts" >> "$FILE"
    git add "$FILE"
    git commit --date="$ts" -m "Work on $ts"
  done

  start=$((start + 86400))
done

git push

