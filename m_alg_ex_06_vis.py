import numpy as np
import matplotlib.pyplot as plt



SHOW_FIGURES = False

def create_amazing_plot(x_range, y_range, plot_title = 'Amazing Plot'):
	plt.xlabel("x1")
	plt.ylabel("x2")

	# plt.axis(0, 20, 0, 20)
	plt.xlim(x_range[0],x_range[1])
	plt.ylim(y_range[0],y_range[1])

	plt.title(plot_title)
	plt.grid(color='gray', linestyle=':')

	plt.xticks([x for x in range(x_range[0],x_range[1])])
	plt.yticks([y for y in range(y_range[0],y_range[1])])

	
	plt.gca().set_aspect('equal', adjustable='box')

# # plt.legend()
create_amazing_plot((5,17), (0,9), "(a)")

# plot 1
plt.plot([x for x in range(5, 11)], [y for y in range(0,6)][::-1], color = 'lightslategray')
plt.plot([5] + [x for x in range(5,17) if x%2==0], [5.5] + [y for y in range(0,6)][::-1], color = 'darkorange')
# plt.fill_between([x for x in range(5, 11)], [y for y in range(0,6)][::-1], color = 'lightslategray', alpha = '0.3')

plt.savefig("ex06_plot_a")
plt.close()

if SHOW_FIGURES:
	plt.show()



# plot 2
create_amazing_plot((0,11), (0,11), "(b)")
plt.plot([5 for x in range(0,12)], [x for x in range(0,12)])
plt.plot([x for x in range(0,6)], [y for y in range(5,11)][::-1], color = 'gold')
plt.plot([x for x in range(0,6) if x%2==0] + [5], [y for y in range(6,9)][::-1] + [5.5], color = 'lightslategray')
plt.fill_between([x for x in range(0,6)], [8,7.5,7,6.5,6,5], color = 'lightslategray', alpha = '0.3')

plt.savefig("ex06_plot_b")
plt.close()

if SHOW_FIGURES:
	plt.show()




# plot 3
create_amazing_plot((0,11), (0,11), "(c)")
plt.plot([5 for x in range(0,12)], [x for x in range(0,12)])
plt.plot([x for x in range(0,6)], [y for y in range(5,11)][::-1], color = 'gold')
plt.plot([x for x in range(0,6) if x%2==0] + [5], [y for y in range(6,9)][::-1] + [5.5], color = 'lightslategray')
xs = [x for x in range(0,6)]
ys = [10,9,8,7,6,5.5]
zs = [11,11,11,11,11,11]
plt.fill_between(xs, ys, zs, color = 'lightslategray', alpha = '.3', interpolate = True)
plt.savefig("ex06_plot_c")
plt.close()
if SHOW_FIGURES:
	plt.show()





# plot 4
create_amazing_plot((0,11), (0,11), "(d)")
plt.plot([5 for x in range(0,12)], [x for x in range(0,12)])
plt.plot([x for x in range(0,6)], [y for y in range(5,11)][::-1], color = 'gold')
plt.plot([x for x in range(0,6) if x%2==0] + [5], [y for y in range(6,9)][::-1] + [5.5], color = 'lightslategray')
plt.fill_between([x for x in range(0,6)], [8,7.5,7,6.5,6,5], color = 'lightslategray', alpha = '0.3')

plt.savefig("ex06_plot_d")
plt.close()

if SHOW_FIGURES:
	plt.show()


print("\n...\n")