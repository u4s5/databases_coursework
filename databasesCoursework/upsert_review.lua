local Count = 0
						--film id
for Index, Value in pairs(redis.call('hgetall', 'film:' .. KEYS[5])) do
  Count = Count + 1
end
if Count == 0 then 
	return -1
end

Count = 0
						--review id
for Index, Value in pairs(redis.call('hgetall', 'review:' .. KEYS[6])) do
  Count = Count + 1
end
if Count == 0 then
	Count = redis.call('get', 'counters:reviews')
else Count = KEYS[6]
end

redis.call('hmset', 'review:' .. tostring(Count), 'author', KEYS[1],
'mark', KEYS[2], 'date', KEYS[3], 'text', KEYS[4], 'film_id', KEYS[5]);

redis.call('set', 'counters:reviews', Count + 1);

return Count

